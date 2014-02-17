![Servando logo](http://proxectos.citius.usc.es/servando/wp-content/images/servando_platform_logo.png)

This repository contains the core modules and APIs provided by the Servando Platform for developing new services.

Developing services
---------------------
The Servando Platform allows the development of different kinds of services, from simple therapy reminders to the monitoring of complex physiological parameters and biosignals, all integrated under a single, simple user interface.
Each service can use different components of the API depending on their specific needs, so below are shown the main developing tasks that are common in the service development process:

### Create a gradle android module
Although this is not a mandatory requirement, it is recommended to keep each service as a separate module.

### Implement the service interface
The main and only requirement when creating a service is the implementation of the [`IPlatformService`](src/main/src/es/usc/citius/servando/android/models/services/IPlatformService.java) interface, where the service provides an identifier, the list of actions it can perform, and a way to create executions of those actions.
Although a service has no an user interface itself, an icon and a name to use in some menus can be provided by the service by extending the [`Iconnable`](src/main/src/es/usc/citius/servando/android/ui/Iconnable.java) API class.

#### Accessing storage
Servando offers an easy way of managing files under the platform working directory. Services that implement the [`StorageAvailableService`](src/main/src/es/usc/citius/servando/android/models/services/StorageAvailableService.java) interface are able to get an [`ServiceStorageHelper`](src/main/src/es/usc/citius/servando/android/services/ServiceStorageHelper.java) that can be used to create and access files under its own folder on the external storage.

#### Communicating with the server
In the same way, services wishing to send information to the server over the given communications module, must implement the [`CommunicableService`](src/main/src/es/usc/citius/servando/android/communications/CommunicableService.java) interface. This allows the service to get an [`ObjectTransporter`](src/main/src/es/usc/citius/servando/android/communications/ObjectTransporter.java) they can use to directly send Java objects to the corresponding service endpoint on the server side.
The communications module is based on WS and the information is transmitted as XML, so classes to send must be properly annotated with [SimpleXML](http://simple.sourceforge.net/), the framework used to provide XML serialization.

### Providing medical actions
As seen before, each service can provide a list of supported medical actions implementing the `IPlatformService` interface. This actions are commonly tasks that must be performed at a certain time, periodically, etc., and that can require user interaction to complete it. Due to this, there are to related concepts that need to be fully understood before continuing:

* A medical action ([`MedicalAction`](src/main/src/es/usc/citius/servando/android/models/protocol/MedicalAction.java) class) models a task susceptible of being executed. They have general properties like an id, a name, a description, etc.
* A medical action execution ([`MedicalActionExecution`](src/main/src/es/usc/citius/servando/android/models/protocol/MedicalActionExecution.java) class) represents a concrete execution of a medical action at a given moment.

If we think for example in a service of questionnaires that provides a medical action for doing tests, an user could be performing two different tests at the same time, and each of them would be a different medical action execution.
Given the variety of actions that could potentially be implemented, each service is responsible of creating the medical action executions when requested by the platform.

#### Providing an UI
Actions that need user interaction must extend the [`Iconnable`](src/main/src/es/usc/citius/servando/android/ui/Iconnable.java) interface, and provide a name, an icon, and a [`ActionExecutionViewFactory`](src/main/src/es/usc/citius/servando/android/ui/ActionExecutionViewFactory.java) that will be used by the platform to create the action view at the appropriate time.
Tha platform provides the [`ServiceFragmentView`](src/main/src/es/usc/citius/servando/android/ui/ServiceFragmentView.java) class that can be extended to create the view of an specific medical action. From this class the developers have access to the ongoing medical action execution, can interact with it, and finish it when the user performs the required tasks.

> For security reasons, only the class implementing the service can access resources like an `ObjectTrasporter` or a `ServiceStorageHelper`, so it is service responsibility to give their medical actions access to this objects when they need it.
> An easy way of doing this is through a singleton that holds references to this components, which are initially set up by the service itself.
