(ns learn-cljs.chat.components.app
  (:require [learn-cljs.chat.components.header :refer [init-header]]
            [goog.dom :as gdom])
  (:import [goog.dom TagName]))

(defn init-main []
  (gdom/createDom TagName.SECTION "content-main"
                  (init-header)))

(defn init-app [el msg-ch]
  (let [wrapper (gdom/createDom TagName.DIV "app-wrapper"
                                (init-main))]
    (set! (.-innerText el) "")
    (.appendChild el wrapper)))
