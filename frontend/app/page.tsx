export default function Home() {
  return (
    <div className="text-center mt-16">
      <h1 className="text-4xl font-bold text-blue-700 mb-4">Bienvenue sur EtudiantsApp</h1>
      <p className="text-gray-600 mb-8">Gérez vos étudiants et départements facilement.</p>
      <div className="flex justify-center gap-4">
        <a href="/etudiants"
           className="bg-blue-600 text-white px-6 py-3 rounded-lg hover:bg-blue-700 transition">
          Voir les Étudiants
        </a>
        <a href="/departements"
           className="bg-green-600 text-white px-6 py-3 rounded-lg hover:bg-green-700 transition">
          Voir les Départements
        </a>
      </div>
    </div>
  );
}
