using UnityEngine;
using System.Collections;

public class PlayerScript : MonoBehaviour {

	public KeyCode left, right, up, down, jump;
	public Transform groundcheck;

	public float speed, verticalSpeed, jumpTime;

	private float timeJumping;

	private Quaternion initialRotation;

	// Use this for initialization
	void Start () {
		initialRotation = transform.rotation;
		timeJumping = 0;
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

		if (Input.GetKey (jump)) {
			if(Physics2D.Linecast(transform.position, groundcheck.position, 1 << LayerMask.NameToLayer("Escenari")))
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

	void OnCollisionStay(Collision collisionInfo) {
		// Debug-draw all contact points and normals
		foreach (ContactPoint contact in collisionInfo.contacts) {
			Debug.Log("" + contact.point + "-" + contact.normal);
			//Debug.DrawRay(contact.point, contact.normal, Color.white);
		}
	}
}
