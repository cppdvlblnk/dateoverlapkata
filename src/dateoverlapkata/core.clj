(ns dateoverlapkata.core
  (:gen-class))


;26h oct 
(def millisecshour 3600000)
(def offsetday (.getTime (java.util.Date. 122 9 26 8 0)))
(defn randhouroftheday [] (+ offsetday (* (rand-int 8) millisecshour)))
(defn randslotsize [] (* (inc (rand-int 3)) millisecshour))
(defn rand-hourstrip [](into [](let [slotinit (randhouroftheday) slotsize (randslotsize)][slotinit (+ slotsize slotinit)])))
(defn human-time [millis] (.toString (java.util.Date. millis)))
(defn rand-hourstrips [start finish](
  if (= start finish) nil
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


(defn human-comb_ [msg comb] 
(
  println msg "["
  (human-time ((comb 0) 0))", " 
  (human-time ((comb 0) 1))"] -- [" 
  (human-time ((comb 1) 0))", " 
  (human-time ((comb 1) 1))"]" 
))

(defn createsortedslotscombineandreportoverlap [n] 
(def timeslots (sorted-rand-hourstrips 0 n)) 
(doseq [timeslot timeslots] (println "Event Time Slot: [" (human-time (timeslot 0)) "," (human-time (timeslot 1)) "]"))
(def combs (into [] (timeslotcomb 0 timeslots)))
(doseq [comb combs] (human-comb_ "Combination:"  comb))
(def overlappedslots (timeslotsoverlapped combs))
(doseq [x overlappedslots] (human-comb_ "Overlapped Slot:" x))


;(doseq [timeslots combs] (
;  let [firstimeslot (timeslots 0) lastimeslot (timeslots 1)] 
;  println "Overlapped Time Slot: [" 
;  (.toString (java.util.Date. (firstimeslot 0))) "," (.toString (java.util.Date. (firstimeslot 1))) "] -- [" (.toString (java.util.Date. (lastimeslot 0))) "," (.toString (java.util.Date. (lastimeslot 1)))))

)

;def timeslots (sorted-rand-hourstrips 0 n)) 
  ;(def overlappedtimeslots (into [] (timeslotsoverlapped (into [] (timeslotcomb 0 timeslots)))))
  
  ;(println "Time Slots Sorted:")
  ;(doseq [timeslot timeslots] (human-timeslot (timeslot 0) (timeslot 1)))
  
  ;(println "Time Slots Combined:")
  ;(doseq [combinedtimeslot (into [] (timeslotcomb 0 timeslots))] (human-timeslot (combinedtimeslot 0) (combinedtimeslot 1))) 
  
  ;(println "Overlapped Time Slots Combined:")
  ;(doseq [overlappedtimeslot (into [] overlappedtimeslots)] (println overlappedtimeslot))

  ;) 

  
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