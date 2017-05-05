(ns nbaviz.db-test
  (:require [clojure.test :refer :all]
            [clojure.java.jdbc :as sql]))

(deftest test-db
  (testing "db init"
    (let [addr "postgresql://localhost:5432/nbaviz"]
      (sql/db-do-commands addr (sql/create-table-ddl :testing [[:data :text]]))
      (let [[row] (sql/insert! addr :testing {:data "foo"})]
        (is (= (:data row) "foo")))
      (let [[row] (sql/query addr ["SELECT * FROM testing"])]
        (is (= (:data row) "foo")))
      (let [[ret] (sql/db-do-commands addr "DROP TABLE testing")]
        (is (= ret 0))))))
