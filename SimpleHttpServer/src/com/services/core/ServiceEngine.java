package com.services.core;

import com.http.server.HttpMethodStates;



public enum ServiceEngine implements Runnable {

	IDLE {
		/**
		 * This state will wait on requests from UI and pops the request and
		 * switches to process state
		 */
		@Override
		void enter() {

			try {

				event = queue.getRequest();

			} catch (ArrayIndexOutOfBoundsException ex) {

				return;
			}

			currstate = PROCESS;

		}
	},
	PROCESS {
		@Override
		void enter() {
			ProcessEvent();
			currstate = IDLE;

		}

		private void ProcessEvent() {
			// System.out.println("input state machine process event:"+event.getRequesttype());
			HttpMethodStates.handleEvent(event);
			ServiceResponseQueue.getInstance().addResponse(event);
			
		}

	};

	abstract void enter();

	protected void Switchstate(ServiceEngine tostate) {

		switch (tostate) {

		case PROCESS:
			PROCESS.enter();
			break;
		case IDLE:
			IDLE.enter();
			break;

		}
	}

	public void start() {
		// currstate = IDLE;
		// currstate.enter();

	}

	public void starteng() {
		currstate = IDLE;

	}

	public void run() {
		currstate = IDLE;
		System.out.println("Service engine started");
		while (true) {
			currstate.enter();
		}
	}

	public ServiceEngine getcurrentstate() {

		return currstate;
	}

	protected static ServiceRequest event;
	private static ServiceEngine currstate;
	private static ServiceRequestQueue queue = ServiceRequestQueue
			.getInstance();

}
