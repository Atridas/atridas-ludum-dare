using UnityEngine;
using System.Collections;

public class lavaScript : MonoBehaviour {

	public void OnTriggerEnter2D (Collider2D other) {
		
		PlayerScript player = other.GetComponent<PlayerScript> ();
		if (player != null) {
			player.die();
		}
	}

}
