(ns learn-cljs.chat.state)

(def initial-state
  {:rooms []
   :people []
   :messages []
   :current-user nil
   :current-view nil
   :auth-modal :sign-in
   :create-room-input-open? false})

(defonce app-state (atom initial-state))

;; app data queries

(defn room-by-id [state id]
  (->> state
       :rooms
       (filter #(= id (:id %)))
       first))

(defn person-by-username [state username]
  (->> state
       :people
       (filter #(= username (:username %)))
       first))

(defn is-current-view-room? [state]
  (= ::room (get-in state [:current-view :type])))

(defn current-room-id [state]
  (get-in state [:current-view :id]))

(defn is-current-view-conversation? [state]
  (= ::conversation (get-in state [:current-view :type])))

(defn current-conversation-recipient [state]
  (get-in state [:current-view :username]))

(defn room-list [state]
  (let [current-room (when (is-current-view-room? state)
                       (get-in state [:current-view :id]))]
    (map (fn [room]
           (assoc room
                  :active? (= current-room (:id room))))
         (:rooms state))))

;; Application data transition functions

(defn received-people-list [state people]
  (assoc state :people people))

(defn person-joined [state person]
  (let [username (:username person)
        is-joined-user? #(= username (:username %))]
    (update state :people
            (fn [people]
              (if (some is-joined-user? people)
                (map
                 (fn [user]
                   (if (is-joined-user? user)
                     (assoc user :online? true)
                     user))
                 people)
                (conj people person))))))

(defn person-left [state username]
  (update state :people
          (fn [people]
            (map #(if (= username (:username %))
                    (assoc % :online? false)
                    %) people))))

(defn received-rooms-list [state rooms]
  (assoc state :rooms rooms))

(defn room-added [state room]
  (update state :rooms conj room))

(defn message-received [state message]
  (update state :messages conj message))

(defn messages-received [state messages]
  (assoc state :messages messages))

(defn messages-cleared [state]
  (assoc state :messages []))
