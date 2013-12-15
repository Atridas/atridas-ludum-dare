using UnityEngine;
using System.Collections;

public class PlayerScript : MonoBehaviour {

	public KeyCode left, right, up, down, jump;
	public Transform groundcheck;

	public float speed, verticalSpeed, jumpTime;

	private float timeJumping;

	private Quaternion initialRotation;
	private Animator anim;
	private bool lookingLeft;

	[ExecuteInEditMode]
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
			lookingLeft = true;
			anim.SetBool("Stopped", false);
			anim.SetFloat("Displacement", velocity.x);
		} else if (Input.GetKey (right)) {
			velocity.x = speed;
			lookingLeft = false;
			anim.SetBool("Stopped", false);
			anim.SetFloat("Displacement", velocity.x);
		} else {
			velocity.x = 0;
			anim.SetBool("Stopped", true);
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

	public void die()
	{
		transform.position = lastSpawner.transform.position;

		GameObject camera = GameObject.FindGameObjectWithTag ("MainCamera");
		CameraScript cameraScript = camera.GetComponent<CameraScript> ();
		cameraScript.closestGuideNode = lastSpawner.cameraNode1;
		cameraScript.closestGuideNode2 = lastSpawner.cameraNode2;
	}
}
