package de.htwg.se.skullking.util

trait Observer {
    def update(e:ObservableEvent): Unit
}

class ObservableEvent {}

class Observable {
  var subscribers:Vector[Observer] = Vector()
  def add(s:Observer): Unit = subscribers = subscribers :+ s
  def remove(s:Observer): Unit = subscribers = subscribers.filterNot(o=>o==s)
  def notifyObservers(e:ObservableEvent = ObservableEvent()): Unit = subscribers.foreach(o => o.update(e))
}
