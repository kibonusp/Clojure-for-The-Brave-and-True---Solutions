(ns chapter-11.core
  (:require [chapter-11.were.multi :refer [full-moon-behavior]]
            [chapter-11.were.records]
            [chapter-11.were.protocols :as p]
            [chapter-11.question-3.records]
            [chapter-11.question-3.protocols :refer :all]
            [chapter-11.rpg :refer [spell-casting]]) 
  (:import (chapter_11.were.records WereSimmons)
            (chapter_11.question_3.records Car Motorcycle))
  (:gen-class))
  
(def car (Car. "Corsa" 2013 5))
(def motorcycle (Motorcycle. "Honda CB750" 1990))

(defn -main
  []
  
  ; Question 1
  (println (full-moon-behavior {:were-type :cameron :name "Quentin Tarantino"}))
  
  ; Question 2
  (println (p/full-moon-behavior (WereSimmons. "David" "Dance Instructor")) "\n")
  
  ; Question 3
  (println (info car))
  (println (move car 80) "\n")
  
  (println (info motorcycle))
  (println (move motorcycle 60) "\n")

  ; Question 4
  (spell-casting :wizard :gryffindor)
  (spell-casting :muggle :slytherin)
)
