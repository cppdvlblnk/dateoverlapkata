(ns dateoverlapkata.core
  (:gen-class))

(defn rand-hourstrip [](into [](sort [(rand-int 10)(rand-int 10)])))

(defn rand-hourstrips [start finish]
(if (= start finish) nil
(cons (rand-hourstrip) (rand-hourstrips (inc start) finish))))

(defn sorted-rand-hourstrips [start finish](into [] (sort (rand-hourstrips start finish)))) 

(defn timeslotcomb [lo s] (
  if (<= (count s) 1) nil
  (let [lastelem (= (inc lo) (dec (count s)))]
  (cons [(first s) (s (inc lo))] (timeslotcomb (if lastelem 0 (inc lo))(if lastelem (into [] (rest s)) s))))
))

(defn printoverlappingslots [timeslots] (doseq [i (range 0 (count timeslots)) :when (> (((timeslots i) 0) 1) (((timeslots i) 1) 0) )] (println "TimeSlots Overlapping: " (timeslots i))))

(defn timeslotsoverlapped [sn] (
  for [i (range 0 (count sn)) :let [s0 ((sn i) 0) s1 ((sn i) 1)] :when (> (s0 1) (s1 0)) ] (sn i) 
))


(defn createsortedslotscombineandreportoverlap [n] (
  def timeslots (into [] (sorted-rand-hourstrips 0 n))) 
  (def overlappedtimeslots (into [] (timeslotsoverlapped (into [] (timeslotcomb 0 timeslots)))))
  
  (println "Time Slots Sorted:")
  (doseq [timeslot timeslots] (println timeslot))
  
  (println "Time Slots Combined:")
  (doseq [combinedtimeslot (into [] (timeslotcomb 0 timeslots))] (println combinedtimeslot)) 
  (doseq [overlappedtimeslot (into [] overlappedtimeslots)] (println "TimeSlots Overlapping: " overlappedtimeslot))

  ) 

  
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (if (seq args) (doseq [arg args] (createsortedslotscombineandreportoverlap (Integer. arg))) (throw (Exception. "Usage: lein run [numberofeventstogenerate]")))
  ;(def timeslots (into [] (sorted-rand-hourstrips 0 10)))
  ;(def combinedtimeslots (into [] (timeslotcomb 0 timeslots))) 

  ;(println "Time Slots Sorted:")
  ;(doseq [timeslot timeslots] (println timeslot))
  
  ;(println "Time Slots Combined:")
  ;(doseq [combinedtimeslot combinedtimeslots] (println combinedtimeslot)) 
  ;(printoverlappingslots combinedtimeslots))
)