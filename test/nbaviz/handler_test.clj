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

  (testing "POST player name route"
    (let [response
          (app (-> (mock/request :post "/player"
                                 (json/write-str {"player" "Russell Westbrook"
                                                  "attrs" ["teamname"]}))
                   (mock/content-type "application/json")))]
      (is (= (:status response) 200))
      (let [body (json/read-str (:body response))]
        (is (= (get body "ok") true))
        (is (= (get (first (get body "data")) "teamname")
               "Oklahoma City Thunder")))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
