(ns kafka-playground.producer
  (:require [kinsky.client :as client]
            [kinsky.async :as async]
            [clojure.core.async :as a :refer [go chan <! >!]]))

(Thread/setDefaultUncaughtExceptionHandler
 (reify Thread$UncaughtExceptionHandler
   (uncaughtException [_ thread throwable]
     (println (.getMessage throwable))
    ;; handle exception here
    )))

;; sample
(comment
 {:topic "account" :key :account-a :value {:action :logout}})

(def ex-chan (chan))

(def my-topic "my_topic")
(def ch (async/producer {:bootstrap.servers "localhost:9092"} :keyword :edn))


;; sending a wrong key, i.e. :key 7 (number) blocks the channel
;; and further messages are not being produced
;; exception handling - https://wil.yegelwel.com/Error-Handling-with-Clojure-Async/
(defn produce-a-message [producer-record]
  (go
    (>! ch producer-record)))

;; Exception handler
(go
  (let [e (<! ex-chan)]
    (println "channel ex handler" )
    (println (.getMessage e))
    ;; handle exception
    ))
