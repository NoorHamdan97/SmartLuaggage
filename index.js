// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions = require('firebase-functions');

// The Firebase Admin SDK to access Cloud Firestore.
const admin = require('firebase-admin');
admin.initializeApp();


exports.checkSwitch = functions.https.onRequest(async (req, res) => {    
  let docRef = admin.firestore().collection('Suitcases').doc('a1a1');
  promise = docRef.get();
  p = promise.then(snapshot => {
	data = snapshot.data();
	console.log("data is: ");
	console.log(data);
	Switch = data["sc_Switch"];
	return res.send(Switch);
  })
  .catch(error => {
	console.log(error);
	return res.status(500).send(error);
  })
});

exports.turnOnVacuum = functions.https.onRequest(async (req, res) => {
  let docRef = admin.firestore().collection('Suitcases').doc('a1a1');
  promise = docRef.get();
  p = promise.then(snapshot => {
	data = snapshot.data();
	console.log("data is: ");
	console.log(data);
	old_val = data["sc_Switch"];
	let updateData = docRef.update({sc_Switch: true});
    return res.send({"old" : old_val,
					 "new" : true});
  })
  .catch(error => {
	console.log(error);
	return res.status(500).send(error);
  })
});

exports.turnOffVacuum = functions.https.onRequest(async (req, res) => {
  let docRef = admin.firestore().collection('Suitcases').doc('a1a1');
  promise = docRef.get();
  p = promise.then(snapshot => {
	data = snapshot.data();
	console.log("data is: ");
	console.log(data);
	old_val = data["sc_Switch"];
	let updateData = docRef.update({sc_Switch: false});
    return res.send({"old" : old_val,
					 "new" : false});
  })
  .catch(error => {
	console.log(error);
	return res.status(500).send(error);
  })
});


exports.isUser = functions.https.onRequest(async (req, res) => {
  let docRef = admin.firestore().collection('Helpers').doc('UserCheck');
  promise = docRef.get();
  p = promise.then(snapshot => {
	data = snapshot.data();
	console.log("data is: ");
	console.log(data);
	old_val = data["IsUser"];
	let updateData = docRef.update({IsUser: true});
    return res.send("this is a user");
  })
  .catch(error => {
	console.log(error);
	return res.status(500).send(error);
  })
});

exports.isNotUser = functions.https.onRequest(async (req, res) => {
  let docRef = admin.firestore().collection('Helpers').doc('UserCheck');
  promise = docRef.get();
  p = promise.then(snapshot => {
	data = snapshot.data();
	console.log("data is: ");
	console.log(data);
	old_val = data["IsUser"];
	let updateData = docRef.update({IsUser: false});
    return res.send("this is not a user");
  })
  .catch(error => {
	console.log(error);
	return res.status(500).send(error);
  })
});

exports.checkUser = functions.https.onRequest(async (req, res) => {   
  let docRef = admin.firestore().collection('Helpers').doc('UserCheck');
  promise = docRef.get();
  p = promise.then(snapshot => {
	data = snapshot.data();
	console.log("data is: ");
	console.log(data);
	User = data["IsUser"];
	return res.send(User);
  })
  .catch(error => {
	console.log(error);
	return res.status(500).send(error);
  })
});

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//   functions.logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });
