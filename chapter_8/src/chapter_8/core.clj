(ns chapter-8.core
  (:gen-class))

(def order-details
    {:name "Mitchard Blimmons"
    :email "mitchard.blimmonsgmail.com"}
)

(def order-details-validations
    {:name
        ["Please enter a name" not-empty]

        :email
        ["Please enter an email andress" not-empty

        "Your email address doesn't look like an email address"
        #(or (empty? %) (re-seq #"@" %))
        ]
    }
)

(defn error-messages-for
    "Return a seq of error messages"
    [to-validate message-validator-pairs]
    (map first (filter #(not ((second %) to-validate)) (partition 2 message-validator-pairs)))
)

(defn validate
    "Returns a map with a vector of errors for each key"
    [to-validate validations]
    (reduce (fn [errors validation]
        (let [[fieldname validation-check-groups] validation
            value (get to-validate fieldname)
            error-messages (error-messages-for value validation-check-groups)]
            (if (empty? error-messages)
                errors
                (assoc errors fieldname error-messages))))
    {}
    validations)
)

(defmacro if-valid
    "Handle validation more concisely"
    [to-validate validations errors-name & then-else]
    `(let [~errors-name (validate ~to-validate ~validations)]
        (if (empty? ~errors-name)
            ~@then-else
        )
    )
)

(defmacro when-valid
    [to-validate validations & then-else]
    `(let [error#  (validate ~to-validate ~validations)]
        (when (empty? error#)
            ~@then-else
        )
    )
)

(defmacro my-or
    ([] false)
    ([x] x)
    ([x & next]
        `(let [or# ~x]
            (if or# or# (my-or ~@next))
        )
    )
)

(def character
    {:name "Smooches McCutes"
    :attributes {:intelligence 10
                :strength 4
                :dexterity 5}}
)

(def attr #(comp % :attributes))

(defmacro defattrs
    [& function-list]
    `(do ~@(reduce
        (fn [deflist [name# attr#]]
            (cons `(def ~name# (attr ~attr#)) deflist)
        )
        '()
        (partition 2 function-list)
    ))  
)

(defn -main
    [& args]
    (if-valid order-details order-details-validations my-error-name
        (println :success)
        (println :failure my-error-name)
    )

    ;; Question 1.   when-valid
    (when-valid order-details order-details-validations
        (println :success)
        (println "all is correct")
    )

    (when-valid {:name "Gabriel Freitas" :email "gabriel@gmail.com"} order-details-validations
        (println :success)
        (println "all is correct")
    )

    ;; Question 2.   my-or
    (println (my-or true false))
    (println (my-or false false))


    ;; Question 3.   defattr
    (defattrs c-int :intelligence
              c-str :strength
              c-dex :dexterity)

    (println (c-dex character))
    (println (infix (1 + 2)))
)
