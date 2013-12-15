using UnityEngine;
using System.Collections;

public class SpawnerScript : MonoBehaviour {

	public CameraGuideNode cameraNode1, cameraNode2;

	void OnTriggerEnter2D(Collider2D collider)
	{
		PlayerScript player = collider.GetComponent<PlayerScript> ();
		if(player != null) {
			player.lastSpawner = this;
		}
	}
}
