(ns learn-cljs.chat.components.header
  (:require [clojure.string :as s]
            [goog.dom :as gdom]
            [learn-cljs.chat.components.component :refer [init-component]]
            [learn-cljs.chat.state :as state])
  (:import [goog.dom TagName]))

(defn display-name [person]
  (if person
    (->> person
         ((juxt :first-name :last-name))
         (s/join " "))
    "REMOVED"))

(defn accessor [app]
  (cond
    (state/is-current-view-room? app)
    {:icon "meeting_room"
     :title (-> app
                (get-in [:current-view :id])
                (->> (state/room-by-id app))
                :name)
     :current-user (:current-user app)}

    (state/is-current-view-conversation? app)
    {:icon "person"
     :title (-> app
                (get-in [:current-view :username])
                (->> (state/person-by-username app))
                display-name)
     :current-user (:current-user app)}

    :else
    {:title "Welcome to ClojureScript Chat"}))

(defn render [header {:keys [icon title current-user]}]
  (doto header
    (.appendChild
     (gdom/createDom TagName.H1 "view-name"
                     (gdom/createDom TagName.I "material-icons" icon)
                     title))
    (.appendChild
     (gdom/createDom TagName.DIV "user-name"
                     (when (some? current-user)
                       (display-name current-user))))))

(defn init-header []
  (init-component
   (gdom/createDom TagName.HEADER "app-header")
   :header accessor render))
