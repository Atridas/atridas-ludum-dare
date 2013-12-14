﻿using UnityEngine;
using System.Collections;

public class PlayerScript : MonoBehaviour {

	public KeyCode left, right, up, down;

	public float speed;

	private Quaternion initialRotation;

	// Use this for initialization
	void Start () {
		initialRotation = transform.rotation;
	}
	
	// Update is called once per frame
	void Update () {
		Vector2 velocity = rigidbody2D.velocity;
		if (Input.GetKey (left)) {
				velocity.x = -speed;
		} else if (Input.GetKey (right)) {
				velocity.x = speed;
		} else {
				velocity.x = 0;
		}

		if(Physics.Linecast(transform.position, transform.position - new Vector3(0,-5,0)))
		{
			velocity.y = 10;
		}

		rigidbody2D.velocity = velocity;
		rigidbody2D.angularVelocity = 0;
		transform.rotation = initialRotation;
	}

	void OnCollisionStay(Collision collisionInfo) {
		// Debug-draw all contact points and normals
		foreach (ContactPoint contact in collisionInfo.contacts) {
			Debug.Log("" + contact.point + "-" + contact.normal);
			//Debug.DrawRay(contact.point, contact.normal, Color.white);
		}
	}
}
