using UnityEngine;
using System.Collections;

public class bulletScript : MonoBehaviour {

	// Use this for initialization
	void Start () {
		Destroy (gameObject, 2);
	}
	
	// Update is called once per frame
	void Update () {
	
	}
	
	void OnTriggerEnter2D(Collider2D collider) {
		
	}
	
	void OnCollisionEnter2D(Collision2D collider) {
		string gameObjectTag = collider.gameObject.tag;
		if (gameObjectTag.Equals ("Player")) {
						// --Destroy(this.gameObject);
		} else {
			Destroy(this.gameObject);
		}
	}
}
