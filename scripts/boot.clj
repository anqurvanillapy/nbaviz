(require '[clojure.data.csv :as csv]
         '[clojure.java.io :as io]
         '[clojure.string :as string]
         '[clojure.java.jdbc :as sql])

(with-open [in-file (io/reader "src/nbaviz/resources/datasets.csv")]
  (let [dbaddr "postgresql://localhost:5432/nbaviz"
        tbl (csv/read-csv in-file)]
    (let [[checksum] (first tbl)
          attrs (doall (map vector
                            (second tbl)
                            ["int PRIMARY KEY"  ; rank
                             "varchar(80)"      ; player name
                             "char(9)"          ; season
                             :smallint          ; age
                             "char(3)"          ; team abbre
                             "char(3)"          ; league
                             :smallint          ; games played
                             :smallint          ; points
                             "char(9)"          ; team season
                             "char(3)"          ; team league
                             "varchar(80)"      ; team name
                             "varchar(80)"      ; team coach
                             "char(4)"          ; team from
                             "char(4)"          ; team to
                             :smallint          ; team years
                             :int               ; team games played
                             :int               ; team wins
                             :int               ; team loses
                             :smallint          ; team champions
                             "varchar(80)"      ; arena team name
                             "char(9)"          ; arena start-end
                             "varchar(80)"      ; arena name
                             "varchar(80)"      ; arena location
                             :int]))            ; arena capacity
          tuples (drop 2 tbl)]
      (sql/db-do-commands dbaddr (sql/create-table-ddl :players attrs))
      (doseq [row tuples]
        (let [[rk pn s a ta lg gp p
               ts tlg tn tc tf tt ty tgp tw tl tch
               atn ase an al ac] (doall (map string/trim row))
              ;; FIXME: Cannot bind the lambda to to-int.
              to-int #(try (Integer/parseInt %)
                           (catch NumberFormatException _ 0))]
          ;; Parse some integer-typed data.
          (sql/insert! dbaddr :players nil [(to-int rk)
                                            pn s
                                            (to-int a)
                                            ta lg
                                            (to-int gp)
                                            (to-int p)
                                            ts tlg tn tc tf tt
                                            (to-int ty)
                                            (to-int tgp)
                                            (to-int tw)
                                            (to-int tl)
                                            (to-int tch)
                                            atn ase an al
                                            (to-int ac)]))))))
