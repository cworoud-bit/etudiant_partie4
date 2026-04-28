// Toutes les requêtes passent par l'API Gateway
const API_GATEWAY = 'http://10.0.2.2:8080'; // Android emulator → localhost

export const api = {
  // Départements
  getDepartements: () =>
    fetch(`${API_GATEWAY}/api/departements`).then(r => r.json()),

  // Étudiants
  getEtudiants: () =>
    fetch(`${API_GATEWAY}/api/etudiants`).then(r => r.json()),

  getEtudiantsByDepartement: (departementId: number) =>
    fetch(`${API_GATEWAY}/api/etudiants`).then(r => r.json()).then(
      (list: any[]) => list.filter(e => e.departementId === departementId)
    ),

  getEtudiant: (id: number) =>
    fetch(`${API_GATEWAY}/api/etudiants/${id}`).then(r => r.json()),

  // Notes
  getNotesByEtudiant: (studentId: number) =>
    fetch(`${API_GATEWAY}/api/notes?studentId=${studentId}`).then(r => r.json()),
};
