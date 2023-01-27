(ns solutions.core
    (:gen-class))

(def character
    {:name "Smooches McCutes"
    :attributes {:intelligence 10
                :strength 4
                :dexterity 5}}
)

(def attr #(comp % :attributes))

(defn my-comp
    [& functions]
    (reduce (fn [comp_function function]
        #(function (apply comp_function %&))
        )
        #(apply (last functions) %&)
        (drop-last functions)
    )
)

(defn my-assoc-in
    [m [k & ks] v]
    (if ks
        (assoc m k (my-assoc-in (get m k) ks v))
        (assoc m k v)
    )
)


(defn my-update-in
    [m ks f & args]
    (assoc-in m ks (apply f (get-in m ks) args))
)

(defn -main
    [& args]
    ; Question 1.  attr
    (println ((attr :intelligence) character) "\n")

    ; Question 2.  my-comp
    (println ((my-comp dec inc max) 2 3 5) "\n")

    ; Question 3.  my-assoc-in
    (println (my-assoc-in [{:name "James" :age 26}  {:name "John" :age 43}] [1 :age] 44))
    (println (my-assoc-in {:a 1 :b 2} [:c :d :e] 4) "\n")

    ; Question 4.  Update-in try-out
    (println (update-in {:a 1 :b 2 :c {:d 4}} [:c :d] + 5))
    (println (update-in {:a 1 :b 2 :c {:d 4}} [:c :d] inc) "\n")

    ; Question 5.  my-update-in
    (println (my-update-in {:a 1 :b 2 :c {:d 4}} [:c :d] + 5))
    (println (my-update-in {:a 1 :b 2 :c {:d 4}} [:c :d] inc))
)