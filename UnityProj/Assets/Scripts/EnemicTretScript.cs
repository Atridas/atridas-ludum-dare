using UnityEngine;
using System.Collections;

public class EnemicTretScript : MonoBehaviour {

	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	
	}
	
	void OnCollisionEnter2D(Collision2D collider) {
		string gameObjectTag = collider.gameObject.tag;
		if (gameObjectTag.Equals ("Player")) {
			PlayerScript ps = collider.gameObject.GetComponent<PlayerScript>();
			ps.die();
		} else {
			Destroy(this.gameObject);
		}
	}
}
