(ns kafka-playground.playground
  (:require [kinsky.client :as client]
            [kinsky.async :as async]
            [clojure.core.async :as a :refer [go <! >!]]))

;; sample
(comment
 {:topic "account" :key :account-a :value {:action :logout}})

(def ch (async/producer {:bootstrap.servers "localhost:9092"} :keyword :edn))


;; sending a wrong key, i.e. :key 7 (number) blocks the channel
;; and further messages are not being produced
(defn produce-a-message [producer-record]
  (go
   (>! ch producer-record)))
