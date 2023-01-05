(ns learn-cljs.chat.state)

(def initial-state
  {:rooms []
   :people []
   :messages []
   :current-user nil})

(defonce app-state (atom initial-state))
