(ns nbaviz.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :as resp]
            [ring.middleware.json :as json]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [clojure.java.jdbc :as sql]))

(defroutes app-routes
  (GET "/" [] (resp/redirect "/index.html"))
    (let [db-spec "postgresql://localhost:5432/nbaviz"]
      (GET "/name/:name" [name]
           (resp/response (sql/query db-spec
                                     [(str "SELECT * "
                                           "FROM players "
                                           "WHERE player="
                                           "'" name "'")]))))
  (route/not-found "404 Page Not Found"))

(def app
  (-> app-routes
      (json/wrap-json-body)
      (json/wrap-json-response)
      (wrap-defaults site-defaults)))
