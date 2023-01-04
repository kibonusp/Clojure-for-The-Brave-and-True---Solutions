(ns chapter-3.core
  (:gen-class))

(def body-parts 
    [
        {:name "head" :size 3}
        {:name "eye-0" :size 1}
        {:name "ear-0" :size 1}
        {:name "mouth" :size 1}
        {:name "nose" :size 1}
        {:name "neck" :size 2}
        {:name "shoulder-0" :size 3}
        {:name "upper-arm-0" :size 3}
        {:name "chest" :size 10}
        {:name "back" :size 10}
        {:name "forearm-0" :size 3}
        {:name "abdomen" :size 6}
        {:name "kidney-0" :size 1}
        {:name "hand-0" :size 2}
        {:name "knee-0" :size 2}
        {:name "thigh-0" :size 4}
        {:name "lower-leg-0" :size 3}
        {:name "achilles-0" :size 1}
        {:name "foot-0" :size 2}
    ]
)

(def add100 #(+ 100 %))

(defn dec-maker
    "Function that creates function which decreases a number by value"
    [subtractor]
    #(- % subtractor)
)

(defn mapset
    "Function that works like map, but return is a set"
    [operator array]
    (set (map operator array))
)

(defn matching-parts
    "Function that creates vector with the other 'number' member parts"
    [part number]
    (if (re-find #"-0$" (:name part))
        (loop [iteration 1 final [part]]
            (if (> iteration number)
                final
                (recur
                    (inc iteration)
                    (let [new_part part]
                        (conj final {:name (clojure.string/replace (:name new_part) #"-0$" (str "-" (str iteration))) :size (:size new_part)})
                    )
                )
            )
        )
    )
)

(defn radial-symmetrize
    "Function that symmetrize the body for a radial (5-members) alien"
    [body-parts number]
    (loop [remaining body-parts final []]
        (let [[part & new_remaining] remaining]
            (if (empty? remaining)
                final
                (recur 
                    new_remaining
                    (let [result (matching-parts part number)]
                        (if result
                            (conj final result)
                            (conj final part)
                        )
                    )
                )
            )
        )
    )
)

(defn -main
    [& args]
    ; Question 1
    (println (str "Eu gosto de " "pizza"))
    (println (vector 1 2 3))
    (println (list "a" 1 2))
    (println (hash-map "a" 1 "b" 2) )
    (println (hash-set 1 1 2 2) "\n")

    ; Question 2
    (println (add100 2) "\n")

    ; Question 3
    (def dec9 (dec-maker 9))
    (println (dec9 10) "\n")

    ; Question 4
    (println (mapset inc [1 1 2 2]) "\n")

    ; Question 5
    (println (radial-symmetrize body-parts 4) "\n")

    ; Question 6
    (println (radial-symmetrize body-parts 5))
)
