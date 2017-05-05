(defproject nbaviz "0.1.0-SNAPSHOT"
  :description "NBA stats and visualization app"
  :url "http://github.com/anqurvanillapy/nbaviz"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [ring/ring-defaults "0.2.1"]
                 [ring/ring-json "0.4.0"]
                 [hiccup "1.0.5"]
                 [org.clojure/java.jdbc "0.7.0-alpha3"]
                 [org.postgresql/postgresql "42.0.0"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler nbaviz.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]
        :resource-paths ["src/nbaviz/resources"]}})
