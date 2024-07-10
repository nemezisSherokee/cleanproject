# APPS OF APPS
The current project deploys the applications on a Kubernetes cluster using the apps of apps pattern.

### Steps for installation for MacBook users
* Install Minikube:
  * `brew install minikube`
  * `minikube start`
  * `minikube dashboard &`

* Install ArgoCD:
    * Create the ArgoCD namespace: `kubectl create ns argocd`
    * Install ArgoCD: `kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/v2.5.8/manifests/install.yaml`
    * Test your installation and wait for all pods to be running: `kubectl get all -n argocd`
    * Forward the port of the ArgoCD server: `kubectl port-forward svc/argocd-server -n argocd 7070:443`
    * Get your initial ArgoCD admin password: `kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | base64 -d; echo`

* Create the App Project:
    * Login to the ArgoCD server (username: admin, password: see the previous command): `argocd login localhost:7070`
    * Create the dev namespace: `kubectl create ns dev`
    * Create the first parent app: 
    ```sh
    argocd app create dev3 \
      --dest-namespace argocd \
      --dest-server https://kubernetes.default.svc \
      --repo https://github.com/nemezisSherokee/cleanproject.git \
      --revision feature/deployment-apps-of-apps \
      --path deployments-aoa/app-of-apps/dev
    ```

* Navigate to ArgoCD and synchronize the referenced project:
    * Open the URL https://localhost:7070

* Test your setup:
    * Ensure all services and infrastructure are up and running.
    * Ensure all configs and secrets are created too.
    * Forward an application and test it with Postman or any other API testing tool:
        * `kubectl port-forward svc/orderprocessing -n dev 8081:8080`
        * `kubectl port-forward svc/productcatalog -n dev 8082:8080`
        * `kubectl port-forward svc/apigateway -n dev 9001:8080`

### Not included
The secrets used in this application are not encrypted with Helm secrets. To do this, we need to:
* Locally install Helm secrets plugin:
  ```sh
  helm plugin install https://github.com/jkroepke/helm-secrets --version v4.6.0
  ```
* Locally install SOPS:
  ```sh
  brew install sops
  brew install gnupg
  ```
* Configure both previous installations:
  [https://blog.gitguardian.com/how-to-handle-secrets-in-helm/](https://blog.gitguardian.com/how-to-handle-secrets-in-helm/)
* Create secrets file:
  `secrets.yaml` and `secrets-<ENV>.yaml`
* More importantly, integrate our deployed ArgoCD with SOPS and Helm secrets:
  [https://github.com/jkroepke/helm-secrets/wiki/ArgoCD-Integration#](https://github.com/jkroepke/helm-secrets/wiki/ArgoCD-Integration#)

### To uninstall the complete application
Just run the following command in the terminal:
```sh
for app in $(argocd app list -o name); do argocd app delete $app --yes; done
```