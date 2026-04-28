import React, { useEffect, useState } from 'react';
import {
  View, Text, StyleSheet, FlatList, ActivityIndicator,
  TouchableOpacity, ScrollView, SafeAreaView,
} from 'react-native';
import { api } from '../services/api';

interface Departement { id: number; nom: string; }
interface Etudiant {
  id: number; nom: string; cin: string;
  email: string; age: number; departementNom?: string;
}

export default function HomeScreen() {
  const [departements, setDepartements] = useState<Departement[]>([]);
  const [etudiants, setEtudiants] = useState<Etudiant[]>([]);
  const [selectedDept, setSelectedDept] = useState<Departement | null>(null);
  const [loading, setLoading] = useState(true);
  const [loadingEtudiants, setLoadingEtudiants] = useState(false);

  // Charger les départements au démarrage
  useEffect(() => {
    api.getDepartements()
      .then(data => { setDepartements(data); setLoading(false); })
      .catch(() => setLoading(false));
  }, []);

  // Charger les étudiants quand un département est sélectionné
  useEffect(() => {
    if (!selectedDept) {
      setEtudiants([]);
      return;
    }
    setLoadingEtudiants(true);
    api.getEtudiantsByDepartement(selectedDept.id)
      .then(data => { setEtudiants(data); setLoadingEtudiants(false); })
      .catch(() => setLoadingEtudiants(false));
  }, [selectedDept]);

  if (loading) {
    return (
      <View style={styles.center}>
        <ActivityIndicator size="large" color="#2563EB" />
        <Text style={styles.loadingText}>Chargement des départements...</Text>
      </View>
    );
  }

  return (
    <SafeAreaView style={styles.container}>
      {/* Header */}
      <View style={styles.header}>
        <Text style={styles.headerTitle}>🎓 EtudiantsApp</Text>
      </View>

      {/* Sélecteur de département */}
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>Sélectionner un département</Text>
        <ScrollView horizontal showsHorizontalScrollIndicator={false}
          contentContainerStyle={styles.deptRow}>
          <TouchableOpacity
            style={[styles.deptChip, !selectedDept && styles.deptChipActive]}
            onPress={() => setSelectedDept(null)}>
            <Text style={[styles.deptChipText, !selectedDept && styles.deptChipTextActive]}>
              Tous
            </Text>
          </TouchableOpacity>
          {departements.map(d => (
            <TouchableOpacity
              key={d.id}
              style={[styles.deptChip, selectedDept?.id === d.id && styles.deptChipActive]}
              onPress={() => setSelectedDept(d)}>
              <Text style={[styles.deptChipText, selectedDept?.id === d.id && styles.deptChipTextActive]}>
                {d.nom}
              </Text>
            </TouchableOpacity>
          ))}
        </ScrollView>
      </View>

      {/* Liste des étudiants */}
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>
          {selectedDept ? `Étudiants — ${selectedDept.nom}` : 'Sélectionnez un département'}
        </Text>

        {loadingEtudiants ? (
          <ActivityIndicator color="#2563EB" style={{ marginTop: 16 }} />
        ) : etudiants.length === 0 ? (
          <Text style={styles.emptyText}>
            {selectedDept ? 'Aucun étudiant dans ce département.' : 'Choisissez un département ci-dessus.'}
          </Text>
        ) : (
          <FlatList
            data={etudiants}
            keyExtractor={item => String(item.id)}
            renderItem={({ item }) => (
              <View style={styles.card}>
                <Text style={styles.cardName}>{item.nom}</Text>
                <Text style={styles.cardDetail}>CIN : {item.cin}</Text>
                <Text style={styles.cardDetail}>📧 {item.email}</Text>
                <Text style={styles.cardDetail}>🎂 {item.age} ans</Text>
              </View>
            )}
          />
        )}
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#F8FAFC' },
  center: { flex: 1, justifyContent: 'center', alignItems: 'center' },
  loadingText: { marginTop: 12, color: '#6B7280', fontSize: 14 },

  header: {
    backgroundColor: '#1D4ED8',
    paddingVertical: 16,
    paddingHorizontal: 20,
  },
  headerTitle: { color: '#fff', fontSize: 20, fontWeight: '700' },

  section: { paddingHorizontal: 16, paddingTop: 16 },
  sectionTitle: { fontSize: 14, fontWeight: '600', color: '#374151', marginBottom: 10 },

  deptRow: { flexDirection: 'row', gap: 8, paddingBottom: 4 },
  deptChip: {
    paddingHorizontal: 14, paddingVertical: 8,
    borderRadius: 20, backgroundColor: '#E5E7EB',
    borderWidth: 1, borderColor: '#D1D5DB',
  },
  deptChipActive: { backgroundColor: '#2563EB', borderColor: '#2563EB' },
  deptChipText: { fontSize: 13, color: '#374151', fontWeight: '500' },
  deptChipTextActive: { color: '#fff' },

  card: {
    backgroundColor: '#fff',
    borderRadius: 12, padding: 14,
    marginBottom: 10, elevation: 2,
    shadowColor: '#000', shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.08, shadowRadius: 4,
  },
  cardName: { fontSize: 16, fontWeight: '700', color: '#111827', marginBottom: 4 },
  cardDetail: { fontSize: 13, color: '#6B7280', marginTop: 2 },

  emptyText: { color: '#9CA3AF', fontSize: 14, marginTop: 20, textAlign: 'center' },
});
