﻿using UnityEngine;
using System.Collections;

public class PlayerScript : MonoBehaviour {

	public KeyCode left, right, jump, shot;
	public Transform groundcheck1,groundcheck2,groundcheck3, spawnerLeft, spawnerRight;

	public float speed, verticalSpeed, jumpTime;

	public Rigidbody2D tirPrefab;

	private float timeJumping;

	private Quaternion initialRotation;
	private Animator anim;
	private bool lookingLeft;

	[HideInInspector]
	public SpawnerScript lastSpawner;

	// Use this for initialization
	void Start () {
		initialRotation = transform.rotation;
		timeJumping = 0;
		anim = GetComponent<Animator>();
		lookingLeft = true;
	}
	
	// Update is called once per frame
	void FixedUpdate () {
		Vector2 velocity = rigidbody2D.velocity;
		if (Input.GetKey (left)) {
			velocity.x = -speed;
			anim.SetBool("Stopped", false);
			anim.SetFloat("Displacement", velocity.x);
		} else if (Input.GetKey (right)) {
			velocity.x = speed;
			anim.SetBool("Stopped", false);
			anim.SetFloat("Displacement", velocity.x);
		} else {
			velocity.x = 0;
			anim.SetBool("Stopped", true);
		}

		if (Input.GetKey (jump)) {
			bool canJump = Physics2D.Linecast(transform.position, groundcheck1.position, 1 << LayerMask.NameToLayer("Escenari"));
			if(!canJump) {
				canJump = Physics2D.Linecast(transform.position, groundcheck2.position, 1 << LayerMask.NameToLayer("Escenari"));
			}
			if(!canJump) {
				canJump = Physics2D.Linecast(transform.position, groundcheck3.position, 1 << LayerMask.NameToLayer("Escenari"));
			}

			if(canJump)
			{
				velocity.y = verticalSpeed;
				timeJumping = jumpTime;
			} else if(timeJumping > 0) {
				timeJumping -= Time.deltaTime;
				velocity.y = verticalSpeed * timeJumping / jumpTime;
			} else if(velocity.y > 0) {
				velocity.y = 0;
				timeJumping = 0;
			}
		} else if(velocity.y > 0) {
			velocity.y = 0;
			timeJumping = 0;
		}

		rigidbody2D.velocity = velocity;
		rigidbody2D.angularVelocity = 0;
		transform.rotation = initialRotation;
	}

	void Update()
	{
		if (Input.GetKeyDown (shot)) {
			anim.SetTrigger("disparar");
			if(lookingLeft) {
				Rigidbody2D bulletInstance = Instantiate(tirPrefab, spawnerLeft.position, Quaternion.Euler(new Vector3(0,0,0))) as Rigidbody2D;
				bulletInstance.velocity = new Vector3(-1.5f*speed,0,0);
			} else {
				Rigidbody2D bulletInstance = Instantiate(tirPrefab, spawnerRight.position, Quaternion.Euler(new Vector3(0,0,0))) as Rigidbody2D;
				bulletInstance.velocity = new Vector3(+1.5f*speed,0,0);
			}
		}
	}

	public void goLeft()
	{
		lookingLeft = true;
		anim.SetBool("goingLeft", true);
	}
	
	public void goRight()
	{
		lookingLeft = false;
		anim.SetBool("goingLeft", false);
	}

	public void fiAnim()
	{
		anim.SetTrigger ("fiAnim");
	}

	public void die()
	{
		transform.position = lastSpawner.transform.position;

		GameObject camera = GameObject.FindGameObjectWithTag ("MainCamera");
		CameraScript cameraScript = camera.GetComponent<CameraScript> ();
		cameraScript.closestGuideNode = lastSpawner.cameraNode1;
		cameraScript.closestGuideNode2 = lastSpawner.cameraNode2;
	}
}
