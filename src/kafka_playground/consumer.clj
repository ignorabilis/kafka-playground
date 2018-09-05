(ns kafka-playground.consumer
  (:require [kinsky.client :as client]
            [kinsky.async :as async]
            [clojure.core.async :as a :refer [go chan <! >!]])
  (:import (java.util UUID)))

(let [[ch ch-2] (async/consumer {:bootstrap.servers "localhost:9092"
                                 ;:group.id          (str (UUID/randomUUID))
                                 }
                                :keyword
                                :edn
                                #_(client/keyword-deserializer)
                                #_(client/edn-deserializer))
      topic  "my_topic"]
  (a/go-loop []
    (when-let [record (a/<! ch-2)]
      (println (pr-str "GOT!!!" record))
      (recur)))
  #_(a/put! ch-2 {:op :partitions-for :topic topic})
  (a/put! ch {:op :subscribe :topic topic})
  (a/put! ch {:op :commit})
  #_(a/put! ch {:op :pause :topic-partitions [{:topic topic :partition 0}
                                            {:topic topic :partition 1}
                                            {:topic topic :partition 2}
                                            {:topic topic :partition 3}]})
  #_(a/put! ch {:op :resume :topic-partitions [{:topic topic :partition 0}
                                             {:topic topic :partition 1}
                                             {:topic topic :partition 2}
                                             {:topic topic :partition 3}]})
  #_(a/put! ch {:op :stop}))