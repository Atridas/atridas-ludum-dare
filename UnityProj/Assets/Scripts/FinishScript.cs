using UnityEngine;
using System.Collections;

public class FinishScript : MonoBehaviour {

	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	
	}

	void OnTriggerEnter2D(Collider2D collider) {
		
		PlayerScript player = collider.GetComponent<PlayerScript> ();
		if (player != null) {
			player.gameObject.SetActive(false);
			GetComponent<Animator>().SetTrigger ("FinalAnimation");
			GameObject.FindGameObjectWithTag("MainCamera").camera.enabled = false;
		}
	}

	void onAnimFinish() {

	}
}
