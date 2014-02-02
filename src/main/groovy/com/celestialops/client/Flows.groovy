package com.celestialops.client

/*
 * commonly used flows
 */
class Flows {
  def system = new Systems()
  def job = new Jobs()

  def stageFlow(s, timeout) {
    def id = system.create(s).id 
    def jid = job.stage(id).job
    def running = job.listJobs().jobs.find{ it.jid == jid } 
    assert job.waitUntil(running.tid, Jobs.State.Succesful, timeout) == true
    id
  }

  def destroyFlow(id, timeout) {
    def jid = job.destroy(id).job
    def running = job.listJobs().jobs.find{ it.jid == jid } 
    assert job.waitUntil(running.tid, Jobs.State.Succesful, timeout) == true
  }
}