(ns kafka-playground.consumer
  (:require [kinsky.client :as client]
            [kinsky.async :as async]
            [clojure.core.async :as a :refer [go chan <! >!]])
  (:import (java.util UUID)))

(def topic "t1")
(def ch (async/consumer {:bootstrap.servers "localhost:9092"
                         :group.id          (str (UUID/randomUUID))
                         :duplex?           true
                         :topic             topic}
                        :keyword
                        :edn))

(let []
  (a/go-loop []
    (when-let [record (a/<! ch)]
      (println (pr-str "GOT!!!" record))
      )
    (recur))

  #_(a/put! ch {:op :partitions-for :topic topic})
  #_(a/put! ch {:op :subscribe :topic topic})
  #_(a/put! ch {:op :commit})
  #_(a/put! ch {:op :pause :topic-partitions [{:topic topic :partition 0}
                                              {:topic topic :partition 1}
                                              {:topic topic :partition 2}
                                              {:topic topic :partition 3}]})
  #_(a/put! ch {:op :resume :topic-partitions [{:topic topic :partition 0}
                                               {:topic topic :partition 1}
                                               {:topic topic :partition 2}
                                               {:topic topic :partition 3}]})
  #_(a/put! ch {:op :stop}))

