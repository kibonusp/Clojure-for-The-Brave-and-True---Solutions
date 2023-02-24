(ns chapter-11.question-3.protocols
  (:require [chapter-11.question-3.records])
  (:import (chapter_11.question_3.records Car Motorcycle)))

(defprotocol Vehicle
  (move [this km] "Put vehicle to move")
  (info [vehicle] "Gives vehicle infos"))

(extend-protocol Vehicle
  Car
  (move [this km] (str "Making Vroom Vroom! at " km " km/h"))
  (info [this] (str "Car of model " (:model this) " year " (:year this) " with " (:seats this) " seats")))

(extend-type Motorcycle
  Vehicle
  (move [this km] (str "Making Tick Tick! at " km " km/h"))
  (info [this] (str "Motorcycle of model " (:model this) " year " (:year this))))