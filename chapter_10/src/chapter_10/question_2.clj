(ns chapter-10.question-2)

(defn get-quotes
    [url]
    (re-seq #"<p>.*<\/p>" (slurp url))
)

(defn get-value
    [tag]
    (:value (reduce
        (fn [final char]
            ((case char
               \< #(assoc final :read false)
               \> #(assoc final :read true)
               #(if (:read final)
                    (assoc final :value (str (:value final) char))
                    final
                )
            ))
        )
        {:value "" :read true}
        (seq tag)
    ))
)

(defn count-word
    [quote]
    (reduce
        (fn [counter word]
            (assoc counter word (inc (get counter word 0)))
        )
        {:count 1}
        (clojure.string/split quote #" ")
    )
)

(def result-counter (atom {:count 0}))

(defn quote-word-count
    [number]
    (let [processed (promise)
          quotes (map get-value (get-quotes "http://motherfuckingwebsite.com/"))]
        (when (<= number (count quotes))
            (doseq [quote (take number quotes)]
                (future
                    (swap! result-counter #(merge-with + % (count-word quote)))
                    (if (= (:count @result-counter) number)
                        (deliver processed @result-counter)
                    )
                )
            )
            @processed
        )
    )
)