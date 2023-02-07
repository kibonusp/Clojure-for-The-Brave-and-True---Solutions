(ns chapter-10.question-3)

(def items
    {:healing-potion 
        { :effect #(assoc % :life (:max-life %))
          :type "consumable"
        }
    }
)

(def character-1
    (ref {
        :life 15
        :max-life 40
        :items #{}
    })
)

(def character-2
    (ref {
        :life 40
        :max-life 40
        :items #{:healing-potion}
    })
)

(defn perform-transaction
    [origin destiny item]
    (dosync
        (when (get-in @origin [:items item])
            (alter origin update :items disj item)
            (alter destiny update :items conj item)
        )
    )
)

(defn use-item
    [character item]
    (dosync
        (when (get-in @character [:items item])
            (alter character (get-in items [item :effect]))
            (if (= (get-in items [item :type]) "consumable")
                (alter character update :items disj item)
            )
        )
    )
)