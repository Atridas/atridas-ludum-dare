using UnityEngine;
using System.Collections;

public class ParalaxCutreScrip : MonoBehaviour {

	public Transform cameraRef;

	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
		Vector3 position = transform.localPosition;
		position.x =  22 - 0.13f * cameraRef.position.x;
		position.y = -25 - 0.75f * cameraRef.position.y;

		transform.localPosition = position; 
	}
}
