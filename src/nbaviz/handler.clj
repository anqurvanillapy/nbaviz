(ns nbaviz.handler
  (:require [clojure.string :as string]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :as resp]
            [ring.middleware.json :as json]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [clojure.java.jdbc :as sql]))

;; Local PostgreSQL address.
(def db-spec "postgresql://localhost:5432/nbaviz")

(defroutes app-routes
  (GET "/" [] (resp/redirect "/index.html"))
  (POST "/player" {params :body}
    (let [attrs (get params "attrs")
          player (get params "player")
          cols (if (empty? attrs) "*" (string/join "," attrs))
          query (sql/query db-spec
                           [(str "SELECT DISTINCT " cols " "
                                 "FROM players "
                                 "WHERE player="
                                 "'" player "'")])]
      (resp/response (if (empty? query)
                       {:ok false :error (str player " not found")}
                       {:ok true :data query}))))
  (route/not-found "404 Page Not Found"))

(def app
  (-> app-routes
      (json/wrap-json-body)
      (json/wrap-json-response)
      (wrap-defaults api-defaults)))
