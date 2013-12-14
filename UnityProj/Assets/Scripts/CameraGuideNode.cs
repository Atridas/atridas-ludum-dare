using UnityEngine;
using System.Collections;

public class CameraGuideNode : MonoBehaviour {

	public CameraGuideNode previous, next;
	public float cameraSize = 10;

	// Use this for initialization
	void Start () {
	}
	
	// Update is called once per frame
	void Update () {
	
	}

	void OnDrawGizmos()
	{
		Gizmos.color = Color.yellow;
		Gizmos.DrawSphere(transform.position, 1.0f);

		Gizmos.color = Color.red;
		if (next != null && next.previous == this) {
			Gizmos.DrawLine(transform.position, next.transform.position);
		}
	}

	void OnDrawGizmosSelected()
	{
		Gizmos.color = Color.cyan;
		Gizmos.DrawLine(transform.position + new Vector3(2*cameraSize,cameraSize,0), transform.position+ new Vector3(-2*cameraSize,cameraSize,0));
		Gizmos.DrawLine(transform.position + new Vector3(2*cameraSize,-cameraSize,0), transform.position+ new Vector3(-2*cameraSize,-cameraSize,0));
		Gizmos.DrawLine(transform.position + new Vector3(2*cameraSize,cameraSize,0), transform.position+ new Vector3(2*cameraSize,-cameraSize,0));
		Gizmos.DrawLine(transform.position + new Vector3(-2*cameraSize,cameraSize,0), transform.position+ new Vector3(-2*cameraSize,-cameraSize,0));
	}
}
