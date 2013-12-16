﻿using UnityEngine;
using System.Collections;

public class CameraScript : MonoBehaviour {
	
	public CameraGuideNode closestGuideNode;
	public CameraGuideNode closestGuideNode2;

	public Transform playerPosition;

	public float timeConstant = 0.333f;

	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
		
		Vector3 vectorToClosest1 = closestGuideNode.transform.position - playerPosition.position;
		Vector3 vectorToClosest2 = closestGuideNode2.transform.position - playerPosition.position;
		Vector3 vectorControlPoints = closestGuideNode2.transform.position - closestGuideNode.transform.position;

		float distancePlayerNode1 = vectorToClosest1.magnitude;
		float distancePlayerNode2 = vectorToClosest2.magnitude;
		float distanceControlPoints = vectorControlPoints.magnitude;
		
		//float cosAtPlayer = Vector3.Dot(vectorToClosest1, vectorToClosest2) / (distancePlayerNode1 * distancePlayerNode2);
		float cosAtClosest1 = Vector3.Dot(-vectorToClosest1, vectorControlPoints) / (distancePlayerNode1 * distanceControlPoints);
		float cosAtClosest2 = Vector3.Dot(-vectorControlPoints, -vectorToClosest2) / (distanceControlPoints * distancePlayerNode2);
		
		CameraGuideNode prev = closestGuideNode.previous;
		if (prev != null) {
			Vector3 vectorPrevControlPoints = prev.transform.position - closestGuideNode.transform.position;
			float distancePrevControlPoints = vectorPrevControlPoints.magnitude;

			float cosAtPrevControlPoints = Vector3.Dot(-vectorToClosest1, vectorPrevControlPoints) / (distancePlayerNode1 * distancePrevControlPoints);

			if(cosAtPrevControlPoints > cosAtClosest1) {
				closestGuideNode2 = closestGuideNode;
				closestGuideNode = prev;
				
				vectorToClosest1 = closestGuideNode.transform.position - playerPosition.position;
				vectorToClosest2 = closestGuideNode2.transform.position - playerPosition.position;
				vectorControlPoints = closestGuideNode2.transform.position - closestGuideNode.transform.position;
				
				distancePlayerNode1 = vectorToClosest1.magnitude;
				distancePlayerNode2 = vectorToClosest2.magnitude;
				distanceControlPoints = vectorControlPoints.magnitude;
				
				cosAtClosest1 = Vector3.Dot(-vectorToClosest1, vectorControlPoints) / (distancePlayerNode1 * distanceControlPoints);
				cosAtClosest2 = Vector3.Dot(-vectorControlPoints, -vectorToClosest2) / (distanceControlPoints * distancePlayerNode2);
			}
		}
		
		CameraGuideNode next = closestGuideNode2.next;
		if(next != null) {
			Vector3 vectorNextControlPoints = next.transform.position - closestGuideNode2.transform.position;
			float distanceNextControlPoints = vectorNextControlPoints.magnitude;
			
			float cosAtNextControlPoints = Vector3.Dot(-vectorToClosest2, vectorNextControlPoints) / (distancePlayerNode2 * distanceNextControlPoints);

			if(cosAtNextControlPoints > cosAtClosest2) {
				closestGuideNode = closestGuideNode2;
				closestGuideNode2 = next;
				
				vectorToClosest1 = closestGuideNode.transform.position - playerPosition.position;
				vectorToClosest2 = closestGuideNode2.transform.position - playerPosition.position;
				vectorControlPoints = closestGuideNode2.transform.position - closestGuideNode.transform.position;
				
				distancePlayerNode1 = vectorToClosest1.magnitude;
				distancePlayerNode2 = vectorToClosest2.magnitude;
				distanceControlPoints = vectorControlPoints.magnitude;
				
				cosAtClosest1 = Vector3.Dot(-vectorToClosest1, vectorControlPoints) / (distancePlayerNode1 * distanceControlPoints);
				cosAtClosest2 = Vector3.Dot(-vectorControlPoints, -vectorToClosest2) / (distanceControlPoints * distancePlayerNode2);
			}
		}

		float alpha = distancePlayerNode2 * cosAtClosest2 / distanceControlPoints;

		if (alpha < 0) {
			alpha = 0;
		} else if(alpha > 1) {
			alpha = 1;
		}

		Vector3 desiredCameraPosition = closestGuideNode.transform.position * alpha + closestGuideNode2.transform.position * (1 - alpha);
		desiredCameraPosition.z = -10;
		float desiredSize = closestGuideNode.cameraSize * alpha + closestGuideNode2.cameraSize * (1 - alpha);

		float updateAlpha = Time.deltaTime / (Time.deltaTime + timeConstant);

		transform.position = desiredCameraPosition * updateAlpha + transform.position * (1 - updateAlpha);
		camera.orthographicSize = desiredSize * updateAlpha + camera.orthographicSize * (1 - updateAlpha);
	}
}
