(ns chapter-4.core
    (:gen-class))
(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
    [str]
    (Integer. str)
)

(def conversions {
    :name identity
    :glitter-index str->int
})

(defn convert
    [vamp-key value]
    ((get conversions vamp-key) value)
)

(defn parse
    "Convert a CSV into rows of columns"
    [string]
    (map #(clojure.string/split % #",")
        (clojure.string/split string #"\r\n")
    )
)

(defn mapify
    "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
    [rows]
    (map (fn [unmapped-row]
            (reduce (fn [row-map [vamp-key value]]
                    (assoc row-map vamp-key (convert vamp-key value))
                )
                {}
                (map vector vamp-keys unmapped-row)
            )
        )
        rows
    )
)

(defn glitter-filter
    [minimum-glitter records]
    (map #(:name %) (filter #(>= (:glitter-index %) minimum-glitter) records))
)

(defn append
    [suspect-list new-suspect]
    (conj suspect-list new-suspect)
)

(def validators {
    :check-name #(not(nil? (:name %)))
    :check-glitter #(not(nil? (:glitter-index %)))
})


(defn validate
    ([validators record]
        (validate validators record true)
    )
    ([validators record result]
        (if (empty? validators)
            result
            (recur (rest validators) record (and ((nth (first validators) 1) record) result))  
        )  
    )
)

(defn mapToCSV
    [mapList]
    (clojure.string/join (reduce (fn [result {name :name glitter-index :glitter-index}]
            (conj result (str name "," glitter-index, "\n"))
        )
        []
        mapList
    ))
)

(defn -main
    [& args]
    (def mapList (mapify (parse (slurp filename))))
    (println (glitter-filter 3 mapList))
    (print "\n")
    (println (append mapList {:name "Kibon" :glitter-index 3}))
    (print "\n")
    (println (validate validators {:name "Jacob Black", :glitter-index 3}))
    (print "\n")
    (println (mapToCSV mapList))
)