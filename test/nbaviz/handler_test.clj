(ns nbaviz.handler-test
  (:require [clojure.test :refer :all]
            [clojure.string :as string]
            [clojure.data.json :as json]
            [ring.mock.request :as mock]
            [nbaviz.handler :refer :all]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 302))
      (is (= (last (string/split (get (:headers response) "Location") #"/"))
             "index.html"))))

  (testing "player name route"
    (let [response (app (mock/request :get "/name/Russell%20Westbrook"))]
      (is (= (:status response) 200))
      (is (= (get (first (json/read-str (:body response))) "teamname")
             "Oklahoma City Thunder"))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
