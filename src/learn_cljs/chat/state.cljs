(ns learn-cljs.chat.state)

(def initial-state
  {:rooms []
   :people []
   :messages []
   :current-user nil})

(defonce app-state (atom initial-state))

;; app data queries

(defn room-by-id [state id]
  (->> state
       :rooms
       (filter #(= id (:id %)))
       first))
