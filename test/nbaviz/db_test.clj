(ns nbaviz.db-test
  (:require [clojure.test :refer :all]
            [clojure.java.jdbc :as sql]))

(deftest test-db
  (testing "db init"
    (let [db-spec "postgresql://localhost:5432/nbaviz"]
      (sql/db-do-commands db-spec
                          (sql/create-table-ddl :testing [[:data :text]]))
      (let [[row] (sql/insert! db-spec :testing {:data "foo"})]
        (is (= (:data row) "foo")))
      (let [[row] (sql/query db-spec ["SELECT * FROM testing"])]
        (is (= (:data row) "foo")))
      (let [[ret] (sql/db-do-commands db-spec "DROP TABLE testing")]
        (is (= ret 0))))))
