(ns chapter-11.were.records
  (:require [chapter-11.were.protocols :refer [WereCreature]]))

(defrecord WereSimmons [name title]
  WereCreature
  (full-moon-behavior [x] (str name " will encourage people and sweat to the oldies")))