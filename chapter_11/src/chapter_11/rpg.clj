(ns chapter-11.rpg)

; Sorry for the lack of creativity, no ideas for a RPG game,
; just copied the Sorting Hat from Hogwarts Legacy

(defmulti spell-casting (fn [class house] [class house]))

(defmethod spell-casting [:wizard :gryffindor]
  [_ _]
  (println "You cast a spell with bravery"))

(defmethod spell-casting [:wizard :ravenclaw]
  [_ _]
  (println "You cast a spell with curiosity"))

(defmethod spell-casting [:wizard :slytherin]
  [_ _]
  (println "You cast a spell with ambition"))

(defmethod spell-casting [:wizard :hufflepuff]
  [_ _]
  (println "You cast a spell with loyalty"))

(defmethod spell-casting :default
  [_ _]
  (println "You didn't cast a spell at all"))