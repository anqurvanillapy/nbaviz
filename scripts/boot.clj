(require '[clojure.data.csv :as csv]
         '[clojure.java.io :as io])

(with-open [in-file (io/reader "src/nbaviz/resources/datasets.csv")]
  (doall
    (let [datasets (csv/read-csv in-file)]
      (println datasets))))
