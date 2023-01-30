(ns chapter-7.core
    (:gen-class))

(defn invert-number
    [number]
    (- number (* 2 number))
)

(defn vector->list
    [vector]
    (reverse (reduce (fn [l v] (conj l (if (vector? v) (vector->list v) v))) '() vector))
)

(defn infixMath
    ([expression]
        (infixMath expression 0 true ['+ (first expression)])
    )
    ([expression index new final]
        (if (= index (count expression))
            (vector->list final)
            (if (= (nth expression index) '*)
                (if new
                    (recur expression (+ 2 index) false (conj final (vector '* (nth expression (dec index)) (nth expression (inc index)))))
                    (recur expression (+ 2 index) false (assoc final (dec (count final)) (conj (last final) (nth expression (inc index)))))
                )
                (if (= (nth expression index) '-)
                    (recur expression (+ 2 index) true (conj final (invert-number(nth expression (inc index))) ))
                    (recur expression (inc index) true final)
                )
            )
        )
    )   
)

(defmacro infix
    [expression]
    (infixMath expression)
)

(defn -main
    [& args]
    
    (eval (list (read-string "println") "Gabriel Star-Wars"))
    (println (infix (2 + 3 * 4 * 2 - 5 + 5 * 4)))
)