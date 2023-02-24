(ns chapter-9.core
    (:gen-class))

(def powells-regex #"<h3.*>.*<a.*>.*<\/a>.*<\/h3>")
(def cultura-regex #"<a.*href=\"http.*\/p\".*>.*</a>")

(defn get-site
    [site]
    (slurp site)
)

(defn get-search-url
    [search engine]
    (str ({"powells" "https://www.powells.com/searchresults?keyword=" 
    "cultura" "https://www3.livrariacultura.com.br/busca/?ft="}
    engine
    ) search)
)

(defn get-base-url
    [url]
    ((reduce
        (fn [base-url char]
            (if (= \/ char)
                (if (= (inc (base-url :count)) 3)
                    (assoc base-url :count 3 :string (base-url :string))
                    (assoc base-url :count (inc (base-url :count)) :string (str (base-url :string) char))
                )
                (if (= (base-url :count) 3)
                    (assoc base-url :string (base-url :string))
                    (assoc base-url :string (str (base-url :string) char))
                )
            )
        )
        {:count 0 :string ""}
        (seq url)
    ) :string)
)

(defn get-href
    [a-tag base-url]
    (let [url (clojure.string/join "" (take-while #(not= % \") (rest (drop-while #(not= % \") (seq a-tag)))))]
        (if (= \/ (get url 0))
            (str (get-base-url base-url) url)
            url
        )
    )
)

(defn get-first-result
    [html url result-regex]
    (let [tag (re-find result-regex html)]
        (get-href tag url)
    )
)

(defn search
    [search-str engine]
    (let [url (get-search-url search-str engine)]
        (get-first-result (get-site url) url 
            ({"powells" powells-regex
               "cultura" cultura-regex}
            engine)
        )
    )
)

(defn get-result-from-first-engine
    "Performs search on Powells and Cultura and returns the results
    which comes first"
    [query & engines]
    (let [result (promise)]
        (doseq [engine engines]
            (future (if-let [engine-result (search query engine)]
                (deliver result engine-result)
            ))
        )
        (println "First result: " (deref result 3000 "timed out"))
    )
)

(defn get-all-results
    "Get all results from search page"
    [query engine]
    (let [
        url (get-search-url query engine)
        html (get-site url) 
        regex ({"powells" powells-regex "cultura" cultura-regex} engine)
        tags (re-seq regex html)
        ]
        (map #(get-href % url) tags)
    )
)
(defn get-all-results-from-pages
    "Apply get-all-results to each engine"
    [query & engines]
    (reduce
        (fn [answer engine]
            (assoc answer engine (get-all-results query engine))
        )
        {}
        engines
    )
)

(defn -main
    [& args]

    ; I used these websites instead of google and bing, because nowadays, they are javascript rendered, so it
    ; doesn't return a rendered html

    ; Question 1 and 2
    ; parallel
    (time (get-result-from-first-engine "clojure" "cultura" "powells"))
    ; sequential
    (time (do
        (println (search "clojure" "cultura"))
        (println (search "clojure" "powells"))
    ))

    (println)

    ; Question 3
    (println (get-all-results-from-pages "shakespeare" "powells" "cultura"))

    (shutdown-agents)
)
