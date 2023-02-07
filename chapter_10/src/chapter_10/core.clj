(ns chapter-10.core
    (:gen-class)
    [:require [chapter-10.question-2 :refer [quote-word-count]]
              [chapter-10.question-3 :refer :all]
    ]
)

(defn -main
    [& args]

    ;; Question 1
    (def number (atom 0))
    (dotimes [n 3]
        (swap! number inc)
    )
    (println @number)

    ;; Question 2
    (println (time (quote-word-count 10)))

    ;; Question 3
    (print "\n")
    (println "Character 1:" @character-1)
    (println "Character 2:" @character-2 "\n")
    (perform-transaction character-2 character-1 :healing-potion)
    (println "After transaction:\n")
    (println "Character 1:" @character-1)
    (println "Character 2:" @character-2 "\n")
    (use-item character-1 :healing-potion)
    (println "After using healing potion:\n")
    (println "Character 1:" @character-1)
    (println "Character 2:" @character-2)
)
