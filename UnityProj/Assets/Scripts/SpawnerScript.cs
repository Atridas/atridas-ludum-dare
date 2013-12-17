using UnityEngine;
using System.Collections;

public class SpawnerScript : MonoBehaviour {

	public CameraGuideNode cameraNode1, cameraNode2;
	
	public GameObject[] enemyGroupsToActivate;
	public GameObject[] enemyGroupsToDeactivate;
	//public EnemyScript[] enemies, enemiesToDeactivate;

	void OnTriggerEnter2D(Collider2D collider)
	{
		PlayerScript player = collider.GetComponent<PlayerScript> ();
		if(player != null) {
			player.lastSpawner = this;
			
			foreach(GameObject enemyGroup in enemyGroupsToActivate) {
				if(enemyGroup != null) {
					enemyGroup.SetActive( true );
				}
			}
			foreach(GameObject enemyGroup in enemyGroupsToDeactivate) {
				if(enemyGroup != null) {
					enemyGroup.SetActive( false );
				}
			}
		}
	}
}
