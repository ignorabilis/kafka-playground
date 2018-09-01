(ns kafka-playground.playground
  (:require [kinsky.client :as client]
            [kinsky.async :as async]
            [clojure.core.async :as a :refer [go <! >!]]))

;; sample
(comment
 {:topic "account" :key :account-a :value {:action :logout}})

(def ch (async/producer {:bootstrap.servers "localhost:9092"} :keyword :edn))

(defn send-a-message [topic k v]
  (let [product {:topic topic :key k :value v}])
  (go
   (>! ch {:topic topic :key k :value v})))
